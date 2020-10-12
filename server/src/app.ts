import express, {Express} from 'express'
require('express-async-errors');
import {Options} from "body-parser";
import {registerRoutes} from "./routes/router";

const port = 9999;
const app: Express = express();

const options: Options = {
    limit: '500mb'
}

const apiRouter = express.Router();

app.use(express.urlencoded(options));
app.use(express.json(options));
app.use(express.text(options));
app.use(express.raw(options));

app.use('/api', apiRouter);

registerRoutes(apiRouter);

app.listen(port);
console.log(`App server running, listening at port ${port}`)