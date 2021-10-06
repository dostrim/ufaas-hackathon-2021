/* eslint-disable camelcase */
/* eslint-disable prefer-const */
/* eslint-disable arrow-parens */
/* eslint-disable semi */
/* eslint-disable max-len */
/* eslint-disable padded-blocks */
/* eslint-disable no-trailing-spaces */
/* eslint-disable indent */
/* eslint-disable object-curly-spacing */
/* eslint-disable quotes */
/* eslint-disable no-unused-vars */
const functions = require("firebase-functions");
const firebase = require("firebase-admin");
const express = require('express');
const cors = require('cors');
const UssdMenu = require('ussd-menu-builder');
const bodyParser = require('body-parser');
const dot = require('dot-object');
const { addPlusDialingCodeToPhoneNumber, capitalizeFirstLetterOfAllWords } = require('./utils');


const serviceAccount = require("./serviceAccount.json");
const { messaging } = require("firebase-admin");
firebase.initializeApp({
    credential: firebase.credential.cert(serviceAccount),
});
const firestore = firebase.firestore();
firestore.settings({ timestampsInSnapshots: true });


const app = express();
app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.post('/', async (request, response) => {

    const args = {
        sessionId: request.body.sessionId,
        serviceCode: request.body.serviceCode,
        phoneNumber: request.body.phoneNumber,
        text: request.body.text || '', // UssdMenu lib throws error when text is null or absent so set default to empty string
    };

    let menu = new UssdMenu();

    try {

        const fetchCreateSession = payload => {
            let session = {
                ussd_session_id: payload.ussd_session_id,
                created_at: Date.now(),
                created_on_backend_at: firebase.firestore.FieldValue.serverTimestamp(),
                user: {
                    id: '',
                    name: '',
                    phoneNumber: '',
                    location: '',
                },
                state: {
                },
            }

            console.log(session)
            return firestore.collection('sessions').add(session);
        }

        const fetchUpdateSession = async payload => {
            let updates = dot.dot(payload);

            let session = await fetchGetSessionIfLive(args.sessionId)

            return firestore.collection('sessions')
                .doc(session.id)
                .update(updates, { merge: true });
        }

        const fetchGetSessionIfLive = ussd_session_id => {
            let session;
            return firestore.collection('sessions').where('ussd_session_id', '==', ussd_session_id).get()
                .then(snapShot => {
                    if (!snapShot.empty) {
                        snapShot.forEach(doc => {
                            session = { id: doc.id, ...doc.data() };
                        })
                        return session;
                    }
                }).catch(error => console.log(error))
        }

        const fetchCreateUser = payload => {
            let user = { ...payload }

            return firestore.collection('users').add(user);
        }

        const fetchUser = phoneNumber => {
            let user = null;
            return firestore.collection('users').where('phoneNumber', '==', phoneNumber).get()
                .then(snapShot => {
                    if (!snapShot.empty) {
                        snapShot.forEach(doc => {
                            user = { id: doc.id, ...doc.data() };
                        })
                    }
                    return user;
                }).catch(error => {
                    console.log(error)
                })
        }

        const fetchCreateRequest = payload => {
            let request = {
                user: {...payload.user},
                service: {...payload.service},
                request_timestamp_millis: payload.created_at,
                state: 'NEW',
            }

            return firestore.collection('requests').add(request);
        }

        menu.startState({
            run: async () => {
                await fetchCreateSession({
                    ussd_session_id: args.sessionId,
                    phoneNumber: args.phoneNumber,
                })
                let session = await fetchGetSessionIfLive(args.sessionId);
                let user = await fetchUser(args.phoneNumber);
                if (user == null && user == undefined) {
                    await fetchUpdateSession({ user: { id: null, name: null, phoneNumber: args.phoneNumber, location: null } })
                    menu.con(`Hello, your number ${args.phoneNumber} is not registered on the platform. Enter 0 to register. \n0: Registration`);
                } else {
                    await fetchUpdateSession({ user: { id: user.id, name: user.name, phoneNumber: user.phoneNumber, location: `${user.district} District,${user.village} Village` } })
                    menu.con(`Hello ${capitalizeFirstLetterOfAllWords(user.name)}, welcome to the FarmSoko menu. Select a option: \n1: Extenstion Services,\n2: Advisory Service,\n3: Search,\n4: My Account`);
                }
            },
            next: {
                0: async () => {
                    await fetchUpdateSession({ service: { type: 'registration' } })
                    return 'registration';
                },
                1: async () => {
                    await fetchUpdateSession({ service: { type: 'extension' } })
                    return 'extensionServices'
                },
                2: async () => {
                    await fetchUpdateSession({ service: { type: 'advisory' } })
                    return 'advisoryServices'
                },
                3: async () => {
                    await fetchUpdateSession({ service: { type: 'search' } })
                    return 'search'
                },
                4: async () => {
                    await fetchUpdateSession({ service: 'account' })
                    return 'account';
                },
            },
        });

        /**
         ************************************ Extension Services ***********************************
         */
        menu.state('extensionServices', {
            run: async () => {
                let session = await fetchGetSessionIfLive(args.sessionId);
                menu.con('EXTENSION SERVICE REQUEST\nSelect a service.\n1: Soil Testing\n2: Ploughing\n3: Ridging\n4: Harrowing\n5: Planting')
            },
            next: {
                1: async () => {
                    await fetchUpdateSession({ service: { request: 'Soil Testing' } })
                    return 'extensionServices.confirm';
                },
                2: async () => {
                    await fetchUpdateSession({ service: { request: 'Ploughing' } })
                    return 'extensionServices.confirm'
                },
                3: async () => {
                    await fetchUpdateSession({ service: { request: 'Ridging' } })
                    return 'extensionServices.confirm'
                },
                4: async () => {
                    await fetchUpdateSession(args.sessionId, { service: { request: 'Harrowing' } })
                    return 'extensionServices.confirm'
                },
                5: async () => {
                    await fetchUpdateSession(args.sessionId, { service: { request: 'Planting' } })
                    return 'extensionServices.confirm'
                },
            },
        });

        menu.state('extensionServices.confirm', {
            run: async () => {
                let session = await fetchGetSessionIfLive(args.sessionId);
                menu.con(`EXTENSION SERVICE REQUEST\nName: ${session.user.name}\nRequest: ${session.service.request}\nEnter your PIN to confirm`);
            },
            next: {
                '*\\d+': async () => { 
                    // TODO verify pin
                    let session = await fetchGetSessionIfLive(args.sessionId);
                    fetchCreateRequest(session)
                    return 'extensionServices.complete';
                },
            },
        });

        menu.state('extensionServices.complete', {
            run: async () => {
                let session = await fetchGetSessionIfLive(args.sessionId);
                menu.end(`EXTENSION SERVICE REQUEST COMPLETED\nYour request has been received and will be processed.`);
            },
            next: {

            },
        });

        /**
         ************************************ Advisory Services ***********************************
         */
        menu.state('advisoryServices', {
            run: async () => {
                let session = await fetchGetSessionIfLive(args.sessionId)
                menu.con('Select a service.\n1: Beans\n2: Cocoa\n3: Coffee\n4: Rice\n5: Citrus\n6: Dairy Cattle\n7: Poultry\n8: Fish Feeds\n9: Apiary\n10: Pigs')
            },
            next: {
                1: async () => {
                    await fetchUpdateSession(args.sessionId, { service: { ser: menu.val } });
                },
            },
        });

        menu.state('advisoryServices.confirm', {
            run: async () => { },
            next: {},
        });

        menu.state('advisoryServices.complete', {
            run: async () => { },
            next: {

            },
        });

        /**
         ************************************ Search for Service ***********************************
         */
        menu.state('search', {
            run: async () => {
                let session = await fetchGetSessionIfLive(args.sessionId)
                menu.con(``);
            },
            next: {
                '*\\w+': async () => { },
            },
        });

        menu.state('search.results', {
            run: async () => {
                let session = await fetchGetSessionIfLive(args.sessionId)
                menu.con(``);
            },
            next: {},
        });

        menu.state('search.confirm', {
            run: async () => {
                menu.end(``);
            },
            next: {},
        });

        /**
         * ************************************ Account ***********************************
         * */
        menu.state('account', {});

        /**
         * ************************************ Registration ***********************************
         * */
        menu.state('registration', {
            run: async () => {
                let session = await fetchGetSessionIfLive(args.sessionId)
                menu.con(`FARMER REGISTRATION\nPhone: ${args.phoneNumber} \nPlease enter your name`)
            },
            next: {
                '*\\w+': async () => {
                    await fetchUpdateSession({ service: { request: 'farmer_registration', form_data: { name: menu.val, phoneNumber: args.phoneNumber } } });
                    return 'registration.district';
                },
            },
        });

        menu.state('registration.district', {
            run: async () => {
                let session = await fetchGetSessionIfLive(args.sessionId)
                menu.con(`FARMER REGISTRATION\nPhone: ${session.service.form_data.phoneNumber}\nName: ${session.service.form_data.name}\nPlease enter district name`)
            },
            next: {
                '*\\w+': async () => {
                    await fetchUpdateSession({ service: { form_data: { district: menu.val } } });
                    return 'registration.county';
                },
            },
        });

        menu.state('registration.county', {
            run: async () => {
                let session = await fetchGetSessionIfLive(args.sessionId)
                menu.con(`FARMER REGISTRATION\nName: ${session.service.form_data.name}\nDistrict: ${session.service.form_data.district}\nPlease enter your county name`)
            },
            next: {
                '*\\w+': async () => {
                    await fetchUpdateSession({ service: { form_data: { county: menu.val } } });
                    return 'registration.subcounty';
                },
            },
        });

        menu.state('registration.subcounty', {
            run: async () => {
                let session = await fetchGetSessionIfLive(args.sessionId)
                menu.con(`FARMER REGISTRATION\nDistric: ${session.service.form_data.district}\nCounty: ${session.service.form_data.county}\nPlease enter your sub-county name`)
            },
            next: {
                '*\\w+': async () => {
                    await fetchUpdateSession({ service: { form_data: { sub_county: menu.val } } });
                    return 'registration.village';
                },
            },
        });

        menu.state('registration.village', {
            run: async () => {
                let session = await fetchGetSessionIfLive(args.sessionId)
                menu.con(`FARMER REGISTRATION\nCounty: ${session.service.form_data.county}\nSub-County: ${session.service.form_data.sub_county}\nPlease enter your village name`)
            },
            next: {
                '*\\w+': async () => {
                    await fetchUpdateSession({ service: { form_data: { village: menu.val } } });
                    return 'registration.farm';
                },
            },
        });

        menu.state('registration.farm', {
            run: async () => {
                let session = await fetchGetSessionIfLive(args.sessionId)
                menu.con(`FARMER REGISTRATION\nSub-County: ${session.service.form_data.sub_county}\nVilage: ${session.service.form_data.village}\nPlease enter your farm eg (Maize, Bananas, Cattle )`)
            },
            next: {
                '*\\w+': async () => {
                    await fetchUpdateSession({ service: { form_data: { farm: menu.val } } });
                    return 'registration.pin';
                },
            },
        })

        menu.state('registration.pin', {
            run: async () => {
                let session = await fetchGetSessionIfLive(args.sessionId)
                menu.con(`FARMER REGISTRATION\nPlease enter your secret 4 digit PIN`)
            },
            next: {
                '*\\d+': async () => {
                    await fetchUpdateSession({ service: { form_data: { pin: menu.val } } });
                    return 'registration.complete';
                },
            },
        });

        menu.state('registration.confirm_pin', {
            run: async () => {
                let session = await fetchGetSessionIfLive(args.sessionId)
                menu.con(`FARMER REGISTRATION\nPlease enter your secret 4 digit PIN to confirm`)
            },
            next: {
                '*\\d+': async () => {
                    await fetchUpdateSession({ service: { form_data: { confirm_pin: menu.val } } });
                    return 'registration.complete';
                },
            },
        });

        menu.state('registration.complete', {
            run: async () => {
                let session = await fetchGetSessionIfLive(args.sessionId)
                await fetchCreateUser(session.service.form_data);

                menu.end(`FARMER REGISTRATION COMPLETE
                \nPhone: ${session.service.form_data.phoneNumber},
                \nName: ${session.service.form_data.name}
                \nDistrict: ${session.service.form_data.district},
                \nCounty: ${session.service.form_data.county},
                \nSub-County: ${session.service.form_data.sub_county},
                \nVillage: ${session.service.form_data.village}
                \nFarm: ${session.service.form_data.farm}`);
            },
        });

        response.send(await menu.run(args));

    } catch (error) {
        console.log(error);
    }
});


exports.ussd = functions.https.onRequest(app);

exports.onCreateNewFarmer = functions.firestore.document('/users/user_id').onCreate(async (snap, context) => {

});

exports.onCreateNewRequest = functions.firestore.document('/requests/request_id').onCreate(async (snap, context) => {

});


