const http = require('http')
const port = 3030
const parseId_fromGetRequest = (url) => {
//  let url = 'http://localhost:3030/pretend?id=2&pause=378&count=1'
  let info = url.split('?')[1]
  let keyvalue = info.split('&')
  let id = keyvalue[0].split('=')[1]
  let pause = keyvalue[1].split('=')[1]
  let count = keyvalue[2].split('=')[1]
  let obj = {
    'id': id,
    'pause_question': pause,
    'count': count
  }
  return obj
}

const burnSomeTime = (obj, request, response) => {
  min = 100
  max = 2000
  let wait = Math.floor(Math.random() * (max - min)) + min
  obj.pause_answer = wait
  setTimeout(sendResponse, wait, [obj, response])
}

function sendResponse (ary) {
  let obj = ary[0]
  let response = ary[1]
  response.writeHead(200, {'Content-Type': 'application/json'})
  console.log('RESPONSE:' + JSON.stringify(obj))
  response.write(JSON.stringify(obj))
  response.end()
}

function onRequest (request, response) {
  const obj = parseId_fromGetRequest(request.url)
  burnSomeTime(obj, request, response)
}

http.createServer(onRequest).listen(port)
console.log('Listening on port ' + port)
