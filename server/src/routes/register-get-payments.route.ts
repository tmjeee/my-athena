import {NextFunction, Router, Request, Response, query} from "express";
import { param } from "express-validator";
import moment from "moment";

const a = [...Array(100).keys()].map((i) => i+1)
const m = moment()

const httpAction: any[] = [
    [
        param("accountId").exists().isNumeric(),
    ],
    (req: Request, res: Response, next: NextFunction) => {
        const accountId = req.params.accountId
        const limit = req.query.limit ? parseInt(req.query.limit as string) : 2;
        const offset = req.query.offset ? parseInt(req.query.offset as string) : 0;

        setTimeout(() => {
            res.status(200).json({
                accountId,
                total: a.length,
                offset,
                limit,
                payments: a.slice(offset, limit + offset).map((i) => {
                    return {
                        paymentId: i,
                        date: m.subtract(i, 'day').format('DD-MM-YYYY'),
                        amount: parseFloat((Math.random() * 100).toFixed(2)),
                        description: `description ${Math.random()}`,
                        from: `From ${Math.random()}`,
                        to: `To ${Math.random()}`
                    }
                })
            });
        }, 5000)

    }
];

export const registerGetPayments = (router: Router) => {

    router.get('/account/:accountId/payments', ...httpAction)
    console.log('** register GET loan statements route')

}