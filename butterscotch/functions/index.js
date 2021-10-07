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
const { messaging } = require("firebase-admin");
const express = require('express');
const cors = require('cors');
const UssdMenu = require('ussd-menu-builder');
const bodyParser = require('body-parser');
const dot = require('dot-object');
const { google } = require('googleapis');
const sheets = google.sheets({ version: 'v4' });
const { addPlusDialingCodeToPhoneNumber, capitalizeFirstLetterOfAllWords } = require('./utils');


const serviceAccount = require("./serviceAccount.json");
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

        const fetchUser = phone_number => {
            let user = null;
            return firestore.collection('users').where('phone_number', '==', phone_number).get()
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
                user: { ...payload.user },
                service: { ...payload.service },
                request_timestamp_millis: payload.created_at,
                state: 'NEW',
            }

            return firestore.collection('requests').add(request);
        }

        const fetchQuery = query => { }

        menu.startState({
            run: async () => {
                await fetchCreateSession({
                    ussd_session_id: args.sessionId,
                    phone_number: args.phoneNumber,
                })
                let session = await fetchGetSessionIfLive(args.sessionId);
                let user = await fetchUser(args.phoneNumber);
                if (user == null && user == undefined) {
                    await fetchUpdateSession({ user: { id: null, name: null, phoneNumber: args.phoneNumber, location: null } })
                    menu.con(`Hello, your number ${args.phoneNumber} is not registered on the platform. Enter 0 to register. \n0: Registration`);
                } else {
                    await fetchUpdateSession({ user: { id: user.id, name: user.name, phoneNumber: user.phone_number, location: `${user.district} District,${user.village} Village` } })
                    menu.con(`Hello ${capitalizeFirstLetterOfAllWords(user.name)}, welcome to the FarmSoko menu. Select a option: \n1: Extension Services,\n2: Advisory Service,\n3: Search,\n4: My Account`);
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
         ************************************ Extension Services Feature ***********************************
         */
        menu.state('extensionServices', {
            run: async () => {
                let session = await fetchGetSessionIfLive(args.sessionId);
                menu.con('EXTENSION SERVICE REQUEST\nSelect a service.\n1: Soil Testing\n2: Ploughing\n3: Ridging\n4: Harrowing\n5: Planting')
            },
            next: {
                1: async () => {
                    await fetchUpdateSession({ service: { request: 'Soil Testing' } })
                    return 'extensionServices.reason';
                },
                2: async () => {
                    await fetchUpdateSession({ service: { request: 'Ploughing' } })
                    return 'extensionServices.reason'
                },
                3: async () => {
                    await fetchUpdateSession({ service: { request: 'Ridging' } })
                    return 'extensionServices.reason'
                },
                4: async () => {
                    await fetchUpdateSession({ service: { request: 'Harrowing' } })
                    return 'extensionServices.reason'
                },
                5: async () => {
                    await fetchUpdateSession({ service: { request: 'Planting' } })
                    return 'extensionServices.reason'
                },
            },
        });

        menu.state('extensionServices.reason', {
            run: async () => {
                let session = await fetchGetSessionIfLive(args.sessionId);
                menu.con(`EXTENSION SERVICE REQUEST\nName: ${session.user.name}\nRequest: ${session.service.request}\nEnter reason`);
            },
            next: {
                '*\\w+': async () => {
                    await fetchUpdateSession({ service: { reason: menu.val } })
                    return 'extensionServices.confirm';
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
         ************************************ Advisory Services Feature ***********************************
         */
        menu.state('advisoryServices', {
            run: async () => {
                let session = await fetchGetSessionIfLive(args.sessionId)
                menu.con('Select a service.\n1: Beans\n2: Cocoa\n3: Coffee\n4: Rice\n5: Citrus\n6: Dairy Cattle\n7: Poultry\n8: Fish Feeds\n9: Apiary\n10: Pigs')
            },
            next: {
                1: async () => {
                    await fetchUpdateSession({ service: { request: 'Beans' } })
                    return 'advisoryServices.reason';
                },
                2: async () => {
                    await fetchUpdateSession({ service: { request: 'Cocoa' } })
                    return 'advisoryServices.reason'
                },
                3: async () => {
                    await fetchUpdateSession({ service: { request: 'Coffee' } })
                    return 'advisoryServices.reason'
                },
                4: async () => {
                    await fetchUpdateSession({ service: { request: 'Rice' } })
                    return 'advisoryServices.reason'
                },
                5: async () => {
                    await fetchUpdateSession({ service: { request: 'Citrus' } })
                    return 'advisoryServices.reason'
                },
                6: async () => {
                    await fetchUpdateSession({ service: { request: 'Dairy Cattle' } })
                    return 'advisoryServices.reason'
                },
                7: async () => {
                    await fetchUpdateSession({ service: { request: 'Poultry' } })
                    return 'advisoryServices.reason'
                },
                8: async () => {
                    await fetchUpdateSession({ service: { request: 'Fish Feeds' } })
                    return 'advisoryServices.reason'
                },
                9: async () => {
                    await fetchUpdateSession({ service: { request: 'Apiary' } })
                    return 'advisoryServices.reason'
                },
                10: async () => {
                    await fetchUpdateSession({ service: { request: 'Pigs' } })
                    return 'advisoryServices.reason'
                },
            },
        });

        menu.state('advisoryServices.reason', {
            run: async () => {
                let session = await fetchGetSessionIfLive(args.sessionId);
                menu.con(`ADVISORY SERVICE REQUEST\nName: ${session.user.name}\nRequest: ${session.service.request}\nEnter reason`);
            },
            next: {
                '*\\w+': async () => {
                    await fetchUpdateSession({ service: { reason: menu.val } })
                    return 'advisoryServices.confirm';
                },
            },
        });

        menu.state('advisoryServices.confirm', {
            run: async () => {
                let session = await fetchGetSessionIfLive(args.sessionId);
                menu.con(`ADVISORY SERVICE REQUEST\nName: ${session.user.name}\nRequest: ${session.service.request}\nEnter your PIN to confirm`);
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

        menu.state('advisoryServices.complete', {
            run: async () => {
                let session = await fetchGetSessionIfLive(args.sessionId);
                fetchCreateRequest(session)
                menu.end(`ADVISORY SERVICE REQUEST COMPLETED\nYour request has been received and will be processed.`);
            },
            next: {

            },
        });

        /**
         ************************************ Search for Service Feature ***********************************
         */
        menu.state('search', {
            run: async () => {
                let session = await fetchGetSessionIfLive(args.sessionId)
                menu.con(`SEARCH\nEnter search query.`);
            },
            next: {
                '*\\w+': async () => {
                    let search_results = await fetchQuery(menu.val)
                    await fetchUpdateSession({ service: { request: menu.val } })
                    return 'search.results';
                },
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
         * ************************************ Account Feature ***********************************
         * */
        menu.state('account', {

        });

        /**
         * ************************************ Registration Feature ***********************************
         * */
        menu.state('registration', {
            run: async () => {
                let session = await fetchGetSessionIfLive(args.sessionId)
                menu.con(`FARMER REGISTRATION\nPhone: ${args.phoneNumber} \nPlease enter your name`)
            },
            next: {
                '*\\w+': async () => {
                    await fetchUpdateSession({ service: { request: 'farmer_registration', form_data: { name: menu.val, phone_number: args.phoneNumber } } });
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
                menu.con(`FARMER REGISTRATION\nSub-County: ${session.service.form_data.sub_county}\nVilage: ${session.service.form_data.village}\nSelect what you farm \n1: Crop\n2: Livestock\n3: Mixed`)
            },
            next: {
                1: async () => {
                    await fetchUpdateSession({ service: { form_data: { farm: 'crops', crops: '', livestock: '' } } });
                    return 'registration.farm.crop';
                },
                2: async () => {
                    await fetchUpdateSession({ service: { form_data: { farm: 'livestock', crops: '', livestock: '' } } });
                    return 'registration.farm.livestock';
                },
                3: async () => {
                    await fetchUpdateSession({ service: { form_data: { farm: 'mixed', crops: '', livestock: '' } } });
                    return 'registration.pin';
                },
            },
        })

        menu.state('registration.farm.crop', {
            run: async () => {
                let session = await fetchGetSessionIfLive(args.sessionId)
                menu.con(`FARMER REGISTRATION\nSelect main crop\n1: Coffee\n2: Cassava\n3: Maize\n4: Bananas\n5: Cocoa`)
            },
            next: {
                1: async () => {
                    await fetchUpdateSession({ service: { form_data: { crops: 'Coffee' } } });
                    return 'registration.pin';
                },
                2: async () => {
                    await fetchUpdateSession({ service: { form_data: { crops: 'Cassava' } } });
                    return 'registration.pin';
                },
                3: async () => {
                    await fetchUpdateSession({ service: { form_data: { crops: 'Maize' } } });
                    return 'registration.pin';
                },
                4: async () => {
                    await fetchUpdateSession({ service: { form_data: { crops: 'Bananas' } } });
                    return 'registration.pin';
                },
                5: async () => {
                    await fetchUpdateSession({ service: { form_data: { crops: 'Cocoa' } } });
                    return 'registration.pin';
                },
            },
        })

        menu.state('registration.farm.livestock', {
            run: async () => {
                let session = await fetchGetSessionIfLive(args.sessionId)
                menu.con(`FARMER REGISTRATION\nSelect main livestock\n1: Beef Cattle\n2: Dairy Cattle\n3: Goats\n4: Poultry\n5: Piggery`)
            },
            next: {
                1: async () => {
                    await fetchUpdateSession({ service: { form_data: { livestock: 'Beef Cattle' } } });
                    return 'registration.pin';
                },
                2: async () => {
                    await fetchUpdateSession({ service: { form_data: { livestock: 'Dairy Cattle' } } });
                    return 'registration.pin';
                },
                3: async () => {
                    await fetchUpdateSession({ service: { form_data: { livestock: 'Goats' } } });
                    return 'registration.pin';
                },
                4: async () => {
                    await fetchUpdateSession({ service: { form_data: { livestock: 'Poultry' } } });
                    return 'registration.pin';
                },
                5: async () => {
                    await fetchUpdateSession({ service: { form_data: { crop: 'Piggery' } } });
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

exports.onCreateNewFarmer = functions.firestore.document('/users/{user_id}').onCreate(async (snap, context) => {

});

exports.onCreateNewRequest = functions.firestore.document('/requests/{request_id}').onCreate(async (snap, context) => {
    const request = {id: snap.id, ...snap.data()}
    // TODO improvement required
    sheets.spreadsheets.values.append({
        spreadsheetId: serviceAccount.gsheet_id,
        range: "Requests",
        auth: new google.auth.JWT(
            serviceAccount.client_email,
            null,
            serviceAccount.private_key,
            ['https://www.googleapis.com/auth/spreadsheets'],
        ),
        key: serviceAccount.api_key,
        valueInputOption: 'RAW',
        resource: { values: [[
            request.id,
            request.request_timestamp_millis, 
            request.service.type, 
            request.service.request, 
            request.service.reason, 
            request.state, 
            request.user.name, 
            request.user.phoneNumber, 
            request.user.location]] },
    }, (err, result) => {
        if (err) {
            throw err;
        } else {
            console.log('Updated sheet: ' + result.data.updates.updatedRange);
        }
    })
});


