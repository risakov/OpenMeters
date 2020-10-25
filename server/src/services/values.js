const e = require('express');
const config = require('../../config.json');
const db = require('../helpers/db');
const meterService = require('./meter')

module.exports = {
    getAll,
    getById,
    create,
    delete: _delete
};

async function getAll() {
    return await db.Value.findAll();
}

async function getById(id) {
    return await getValue(id);
}

async function create(params) {    
    await db.Value.create(params);
}

async function _delete(id) {
    const value = await getValue(id);
    await value.destroy();
}

async function getValue(id) {
    const value = await db.Value.findByPk(id);
    if (!value) throw 'not found';
    return value;
}
