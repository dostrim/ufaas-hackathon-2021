/* eslint-disable keyword-spacing */
/* eslint-disable quotes */
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
const phoneRegex = /^0([6-7]{1}([0-9]{2})[0-9]{6})+$/;
const countryCodeUganda = '+256';

exports.addPlusDialingCodeToPhoneNumber = (phoneNumber = '') => {
    if (phoneNumber.startsWith('+256') || phoneNumber.startsWith('256')) {
        // these phone numbers almost certainly start with an area code
        return phoneNumber
    }else{
        return countryCodeUganda + phoneNumber.substring(1)
    }
};

exports.capitalizeFirstLetterOfAllWords = (string = "") => {
    if (!((typeof string) === "string")) {
        if (typeof string === "number") string = string.toString();
        else return
    }
    // collapse multiple spaces into one space
    string = string.replace(/\s\s+/g, ' ');
    const words = string.split(" ");

    return words.reduce((capitalizedWords, word) => {
        word = word.toLocaleLowerCase();
        word = word.charAt(0).toUpperCase() + word.slice(1);
        if (capitalizedWords) word = " " + word;
        capitalizedWords = `${capitalizedWords}${word}`;
        return capitalizedWords
    }, "")
};

