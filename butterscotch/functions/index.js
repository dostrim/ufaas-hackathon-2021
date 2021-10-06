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
                session_id: payload.session_id,
                created_at: Date.now(),
                created_on_backend_at: firebase.firestore.FieldValue.serverTimestamp(),
                user_id: '',
                state: {
                },
            }

            console.log(session)
            return firestore.collection('sessions').add(session);
        }

        const fetchUpdateSession = (id, payload) => {
            let updates = payload;
            return firestore.collection('sessions')
                .doc(id)
                .update(updates, { merge: true });
        }

        const fetchGetSessionIfLive = session_id => {
            let session;
            return firestore.collection('sessions').where('session_id', '==', session_id).get()
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
            let user = {}

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
            let request = {}

            return firestore.collection('requests').add(request);
        }

        menu.startState({
            run: async () => {
                await fetchCreateSession({
                    session_id: args.sessionId,
                    phoneNumber: args.phoneNumber,
                })
                let session = await fetchGetSessionIfLive(args.sessionId);
                let user = await fetchUser(addPlusDialingCodeToPhoneNumber(args.phoneNumber));
                if (user == null || user == undefined) {
                    await fetchUpdateSession(session.id, { user_id: null })
                    menu.con(`Hello, your number ${args.phoneNumber} is not registered on the platform. Enter 0 to register`);
                }
                await fetchUpdateSession(session.id, { user_id: user.id })
                menu.con(`Hello ${capitalizeFirstLetterOfAllWords(user.name)}, welcome to the AEAS menu. Select a option: \n1: Extenstion Services,\n2: Advisory Service,\n3: Search,\n4: My Account`);
            },
            next: {
                0: async () => {
                    await fetchUpdateSession(args.sessionId, { service: { type: 'registration' } })
                    return 'registration';
                },
                1: async () => {
                    await fetchUpdateSession(args.sessionId, { service: { type: 'extension' } })
                    return 'extensionServices'
                },
                2: async () => {
                    await fetchUpdateSession(args.sessionId, { service: { type: 'advisory' } })
                    return 'advisoryServices'
                },
                3: async () => {
                    await fetchUpdateSession(args.sessionId, { service: { type: 'search' } })
                    return 'search'
                },
                4: async () => {
                    await fetchUpdateSession(args.sessionId, { service: 'account' })
                    return 'account';
                },
            },
        });

        /**
         ************************************ Extenstion Services ***********************************
         */
        menu.state('extensionServices', {
            run: async () => {
                let session = await fetchGetSessionIfLive(args.sessionId)
                menu.con('Select a service.\n1: Soil Testing\n2: Ploughing\n3: Ridging\n4: Harrowing\n5: Planting')
            },
            next: {
                1: async () => {
                    await fetchUpdateSession(args.sessionId, { service: { extension: 'Soil Testing' } })
                    return 'Soil Testing';
                },
                2: async () => {
                    return 'Ploughing'
                },
                3: async () => {

                    return 'Ridging'
                },
                4: () => {

                    return 'Harrowing'
                },
                5: () => {

                    return 'Planting'
                },
            },
        });

        menu.state('extensionServices.confirm', {
            run: async () => { },
            next: {},
        });

        menu.state('extensionServices.complete', {
            run: async () => { },
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
                menu.con(`FARMER REGISTRATION\nPhone: ${session.phoneNumber} \nPlease enter your name`)
            },
            next: {
                '*\\w+': async () => {
                    await fetchUpdateSession(args.sessionId, { registration: { name: menu.val, phoneNumber: args.phoneNumber } });
                    return 'registration.district';
                },
            },
        });

        menu.state('registration.district', {
            run: async () => {
                let session = await fetchGetSessionIfLive(args.sessionId)
                menu.con(`FARMER REGISTRATION\nPhone: ${session.registration.phoneNumber}\nName: ${session.registration.name}\nPlease enter district name`)
            },
            next: {
                '*\\w+': async () => {
                    await fetchUpdateSession(args.sessionId, { registration: { district: menu.val } });
                    return 'registration.county';
                },
            },
        });

        menu.state('registration.county', {
            run: async () => {
                let session = await fetchGetSessionIfLive(args.sessionId)
                menu.con(`FARMER REGISTRATION\nName: ${session.registration.name}\nDistrict: ${session.registration.district}\nPlease enter your county name`)
            },
            next: {
                '*\\w+': async () => {
                    await fetchUpdateSession(args.sessionId, { registration: { county: menu.val } });
                    return 'registration.subcounty';
                },
            },
        });

        menu.state('registration.subcounty', {
            run: async () => {
                let session = await fetchGetSessionIfLive(args.sessionId)
                menu.con(`FARMER REGISTRATION\nDistric: ${session.registration.district}\nCounty: ${session.registration.county}\nPlease enter your sub-county name`)
            },
            next: {
                '*\\w+': async () => {
                    await fetchUpdateSession(args.sessionId, { registration: { sub_county: menu.val } });
                    return 'registration.village';
                },
            },
        });

        menu.state('registration.village', {
            run: async () => {
                let session = await fetchGetSessionIfLive(args.sessionId)
                menu.con(`FARMER REGISTRATION\nCounty: ${session.registration.county}\nSub-County: ${session.registration.sub_county}\nPlease enter your village name`)
            },
            next: {
                '*\\w+': async () => {
                    await fetchUpdateSession(args.sessionId, { registration: { village: menu.val } });
                    return 'registration.pin';
                },
            },
        });

        menu.state('registration.pin', {
            run: async () => {
                let session = await fetchGetSessionIfLive(args.sessionId)
                menu.con(`FARMER REGISTRATION\nPlease enter your secret 4 digit PIN`)
            },
            next: {
                '*\\d+': async () => {
                    await fetchUpdateSession(args.sessionId, { registration: { pin: menu.val } });
                    return 'registration.confirm_pin';
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
                    await fetchUpdateSession(args.sessionId, { registration: { pin: menu.val } });
                    return 'registration.complete';
                },
            },
        });

        menu.state('registration.complete', {
            run: async () => {
                let session = await fetchGetSessionIfLive(args.sessionId)
                menu.end(`FARMER REGISTRATION COMPLETE
                \nPhone: ${session.registration.phoneNumber},
                \nName: ${session.registration.name}
                \nDistrict: ${session.registration.district},
                \nCounty: ${session.registration.county},
                \nSub-County: ${session.registration.sub_county},
                \nVillage: ${session.registration.village}`);
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


