function getRandomInt(min, max) {
	min = Math.ceil(min);
	max = Math.floor(max);
	return Math.floor(Math.random() * (max - min) + min);
}


const formatMoney = (number) => {
	return String(number || 0)
		.replace(/(.)(?=(\d{3})+$)/g, '$1.')
		.replace(/\-\./g, '-');
}

const formatMoneyForm = (number) => {
	return String(number).replace(/\./g, '').replace(/(.)(?=(\d{3})+$)/g, '$1.');
}

const resetMoneyForm = (number) => {
	return Number(String(number).replace(/\./g, ''));
}

function formatNumberWithCommas(number) {
	return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}
const formatInputMoney = (event) => {
	const inputValue = event.target.value;
	const sanitizedValue = inputValue.replace(/[^0-9]/g, '');
	return formatNumberWithCommas(sanitizedValue);
};
function removeCommas(inputString) {
	if (inputString.length != 0) {
		return inputString.replace(/,/g, "");
	}
}
const randomTradingCode = () => {
	return Math.floor(Math.random() * ((Math.pow(10, 9)) - (Math.pow(10, 10) - 1) + 1)) + (Math.pow(10, 10) - 1);
};
const handleInputPercentPin = (e) => {
	const inputValue = e;
	let numericValue = parseInt(inputValue, 10);
	if (isNaN(numericValue)) {
		numericValue = 0;
	}
	if (numericValue < 1) {
		numericValue = 0;
	} else if (numericValue > 100) {
		numericValue = 100;
	}
	return numericValue;
};




module.exports = { getRandomInt, formatMoney, formatMoneyForm, resetMoneyForm, formatInputMoney, randomTradingCode, removeCommas, handleInputPercentPin,formatNumberWithCommas};