const {PythonShell} = require('python-shell')

runPythonScript = (args) => {
    let options = {
      mode: 'text',
      pythonOptions: [], 
      args: args,
    };
  
    return new Promise((resolve,reject) =>{
      try{
        PythonShell.run('./script1.py', options, (err, results) => {
          if (err) {console.log(err);}
          resolve(results);          
        }); 
      }
      catch{
        console.log('error running python code')
        reject();
      }
    })
}

module.exports = runPythonScript;
