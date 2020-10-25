const config = require('../../config.json');
const db = require('../helpers/db');

module.exports = {
    getAll,
    getById,
    create,
    update,
    delete: _delete
};

async function getAll() {
    return await db.Meter.findAll();
}

async function getById(id) {
    return await getMeter(id);
}

async function create(params) {
    if (params.serialNumber) {
        const result = await db.Meter.findOrCreate({ where: { serialNumber: params.serialNumber, type: params.type } });
        return result
    } else {
        const result = await db.Meter.create(params)
        return result
    }

}

async function update(id, params) {
    const meter = await getMeter(id);

    const serialNumberChanger = params.serialNumber && meter.serialNumber !== params.serialNumber;
    if (serialNumberChanger && await db.Meter.findOne({ where: { serialNumber: params.serialNumber } })) {
        throw 'serialNumber "' + params.username + '" уже использован';
    }

    Object.assign(meter, params);
    await meter.save();
}

async function _delete(id) {
    const meter = await getMeter(id);
    await meter.destroy();
}

async function getMeter(id) {
    const meter = await db.Meter.findByPk(id);
    if (!meter) throw 'not found';
    return meter;
}
