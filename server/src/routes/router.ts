import {Router} from "express";
import {registerLoginRoute} from "./login.route";
import {registerRequestPinRoute} from "./request-pin.route"
import {registerSubmitPinRoute} from "./submit-pin.route"
import {registerGetAccountsRoute} from "./register-get-accounts.route";
import {registerGetAccountTransactionsRoute} from "./get-account-transactions.route";
import {registerGetAccountRoute} from "./get-account.route";
import {registerGetLoanStatements} from "./register-get-loan-statements.route";
import {registerGetLoanDetails} from "./register-get-loan-details.route";
import {registerGetPayments} from "./register-get-payments.route";
import {registerGetPhoto} from "./register-get-photo.route";
import {registerGetPhotoById} from "./register-get-photo-by-id.route";


export const registerRoutes = (router: Router) => {
    console.log('**** registering routes')
    registerRequestPinRoute(router);
    registerLoginRoute(router);
    registerSubmitPinRoute(router);
    registerGetAccountsRoute(router);
    registerGetAccountRoute(router);
    registerGetAccountTransactionsRoute(router);
    registerGetLoanDetails(router);
    registerGetLoanStatements(router);
    registerGetPayments(router);
    registerGetPhoto(router);
    registerGetPhotoById(router);
    console.log('**** done registering routes')
}