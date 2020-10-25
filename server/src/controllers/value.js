const express = require('express');
const router = express.Router();
const Joi = require('joi');
const authorize = require('../middleware/authorize');
const validateRequest = require('../middleware/validateRequest');
const valueService = require('../services/values');

router.get('/', authorize(), getAll);
router.post('/', createSchema, create)
router.get('/:id', authorize(), getById);
router.delete('/:id', authorize(), _delete);

module.exports = router;

function createSchema(req, res, next) {
    const schema = Joi.object({
        meter_id: Joi.required(),
        value: Joi.string().required(),
    });
    validateRequest(req, next, schema);
}

function create(req, res, next) {
    valueService.create(req.body)
        .then(() => res.json({ message: 'OK!' }))
        .catch(next);
}

function getAll(req, res, next) {
    valueService.getAll()
        .then(meters => res.json(meters))
        .catch(next);
}

function getById(req, res, next) {
    valueService.getById(req.params.id)
        .then(meter => res.json(meter))
        .catch(next);
}

function _delete(req, res, next) {
    valueService.delete(req.params.id)
        .then(() => res.json({ message: 'OK' }))
        .catch(next);
}
