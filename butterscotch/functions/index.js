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


