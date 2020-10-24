var express = require('express');
var router = express.Router();
var multer = require('multer')
var upload = multer({ dest: 'uploads/' })
var runPythonScript = require('../utils/runPythonScript')
const meterService = require('../services/meter');

router.get('/', function (req, res) {
    res.json({
        msg: 'API is running'
    });
});

router.post('/uploadImages', async (req, res) => {
    console.log(req.files)
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
                data.push(JSON.parse(pythonResult[0]))
            }
            res.send({
                status: true,
                message: 'Files are uploaded',
                data: data
            });
            console.log(data)
        }
    } catch (err) {
        res.status(500).send(err);
    }
});

router.post('/uploadSingleImage', async (req, res) => {
    console.log(req.files)
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
            const response = (JSON.parse(pythonResult[0]))

            res.json({
                status: true,
                message: 'File is uploaded',
                data: response
            });
            console.log('single', data)
        }
    } catch (err) {
        res.status(500).json(err);
    }
});

module.exports = router;