var express = require('express');
var router = express.Router();
var multer = require('multer')
var upload = multer({ dest: 'uploads/' })
var runPythonScript = require('../utils/runPythonScript')

router.get('/', function (req, res) {
    res.json({
        msg: 'API is running'
    });
});

router.post('/uploadImages', upload.array('file', 12), async function (req, res, next) {
    const response = []
    for(const file of req.files) {
        const pythonResult = await runPythonScript(`/${file.path}`)
        response.push(JSON.parse(pythonResult[0]))
    }
    res.json({result:response})
});

router.post('/uploadSingleImage', upload.single('file'), async function (req, res, next) {
    const pythonResult = await runPythonScript(`/${req.file.path}`)
    const response = (JSON.parse(pythonResult[0]))
    res.json({result:response})
});


module.exports = router;