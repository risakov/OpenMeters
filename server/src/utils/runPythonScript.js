const {PythonShell} = require('python-shell')

runPythonScript = (args) => {
    let options = {
      mode: 'text',
      pythonOptions: [], 
      args: args,
    };
  
    return new Promise((resolve,reject) =>{
      try{
        PythonShell.run('./scriptRecognition.py', options, (err, results) => {
          if (err) {console.log(err);}
          resolve(results);          
        }); 
      }
      catch (err) {
        console.log('error running python code')
        reject(err);
      }
    })
}

module.exports = runPythonScript;
