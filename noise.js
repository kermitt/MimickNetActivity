let count = 0
let limit = 100
const request = require('request')

function callback (error, response, body) {
  if (!error && response.statusCode == 200) {
    let pause = Math.random() * 1000 
    console.log('pause for ' + pause.toFixed(0) + ' msec for id ' + (count + 1))
    setTimeout(send, pause)
  }
}

function send () {
  if (count < limit) {
    count++
    let options = {
      url: 'http://localhost:3030/pretend&id=' + count,
      method: 'GET',
      headers: {}
    }
    request(options, callback)
  } else {
    console.log('Finished sending ' + count)
  }
}
send()
