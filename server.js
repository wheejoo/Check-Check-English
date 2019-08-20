const express = require('express');
const app = express();

app.use(express.static('public'));

app.get('/', function(request, response) {
  response.sendFile(__dirname + '/views/index.html');
});
const listener = app.listen(process.env.PORT, function() {
  console.log('Your app is listening on port ' + listener.address().port);
});


// 1
const bodyParser = require('body-parser');
app.use(bodyParser.json());

// 2
const { WebhookClient } = require('dialogflow-fulfillment');

// 3안녕
app.post('/df', function(request, response) {
    var agent = new WebhookClient({ request, response });
    const {intent,query} = agent;
    console.log ({intent,query});
  
    function testAct (agent) {
      agent.add('서버에서 출력');
    }
  
    let intentMap = new Map();
    intentMap.set('This is a snack!', testAct);
    intentMap.set('This is a snack! - custom', testAct);
    intentMap.set('This is a snack! - custom - custom', testAct);
    intentMap.set('This is a snack! - custom - custom - custom', testAct);
  
    intentMap.set('This is a snack 2', testAct);
    intentMap.set('This is a snack 2 - custom', testAct);
    intentMap.set('This is a snack 2 - custom - custom', testAct);
    intentMap.set('This is a snack 2 - custom - custom - custom', testAct);
    agent.handleRequest(intentMap);
});


