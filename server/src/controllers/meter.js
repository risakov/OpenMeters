const express = require('express');
const router = express.Router();
const Joi = require('joi');
const authorize = require('../middleware/authorize');
const validateRequest = require('../middleware/validateRequest');
const meterService = require('../services/meter');

router.get('/', authorize(), getAll);
router.post('/', authorize(), createSchema, create)
router.get('/:id', authorize(), getById);
router.put('/:id', authorize(), updateSchema, update);
router.delete('/:id', authorize(), _delete);

module.exports = router;

function createSchema(req, res, next) {
    const schema = Joi.object({
        type: Joi.string().required(),
        serialNumber: Joi.string()
    });
    validateRequest(req, next, schema);
}

function create(req, res, next) {
    meterService.create(req.body)
        .then(() => res.json({ message: 'OK!' }))
        .catch(next);
}

function getAll(req, res, next) {
    meterService.getAll()
        .then(meters => res.json(meters))
        .catch(next);
}

function getById(req, res, next) {
    meterService.getById(req.params.id)
        .then(meter => res.json(meter))
        .catch(next);
}

function updateSchema(req, res, next) {
    const schema = Joi.object({
        serialNumber: Joi.string().empty(''),
    });
    validateRequest(req, next, schema);
}

function update(req, res, next) {
    meterService.update(req.params.id, req.body)
        .then(meter => res.json(meter))
        .catch(next);
}

function _delete(req, res, next) {
    meterService.delete(req.params.id)
        .then(() => res.json({ message: 'OK' }))
        .catch(next);
}
