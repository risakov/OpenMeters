var express = require('express');
var router = express.Router();
var runPythonScript = require('../utils/runPythonScript')
// var meterService = require('../services/meter')
// var valueService = require('../services/values')

router.get('/', function (req, res) {
    res.json({
        msg: 'API is running'
    });
});

router.post('/uploadImages', async (req, res) => {
    try {
        if(!req.files) {
            res.send({
                status: false,
                message: 'No file uploaded'
            });
        } else {
            let data = [];

            //loop all files
            for(const file of req.files.photos) {
                file.mv('./uploads/' + file.name);
                const pythonResult = await runPythonScript(`/uploads/${file.name}`)
                const parsedResult = JSON.parse(pythonResult[0])
                data.push(parsedResult)
            }

            res.send({
                status: true,
                message: 'Files are uploaded',
                data: data
            });
        }
    } catch (err) {
        res.status(500).send(err);
    }
});

router.post('/uploadSingleImage', async (req, res) => {
    try {
        if(!req.files) {
            res.status(400).json({
                status: false,
                message: 'No file uploaded'
            });
        } else {

                let meter = req.files.file;
                meter.mv('./uploads/' + meter.name);
                const pythonResult = await runPythonScript(`/uploads/${meter.name}`)
                const parsedResult = JSON.parse(pythonResult[0])

                const response = parsedResult

                res.json({
                    status: true,
                    message: 'File is uploaded',
                    data: [response]
                });
        }
    } catch (err) {
        res.status(500).json(err);
    }
});

module.exports = router;