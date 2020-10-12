import {NextFunction, Router, Request, Response} from "express";
import { param } from "express-validator";
import moment from 'moment'

const a = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37]
// const b = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15]
const b = [1,2]

const m = moment()

const httpAction: any[] = [
    [
        param('accountId').exists()
    ],
    (req: Request, res: Response, next: NextFunction) => {
        const accountId = parseInt(req.params.accountId);
        const limit = req.query.limit ? parseInt(req.query.limit as string) : 2;
        const offset = req.query.offset ? parseInt(req.query.offset as string) : 0;

        console.log(`GET get account transactions accountId=${accountId} limit=${limit} offset=${offset}`)

        const r = {
            accountId,
            total: a.length,
            offset,
            limit,
            entries: a.slice(offset, limit + offset).map((i) => {
                return {
                    transactionId: i,
                    date: m.subtract(i, 'day').format('DD-MM-YYYY'),
                    entries: b.map((i) => {
                        return {
                            time: m.subtract(1, 'hours').format('hh:mm:ss a'),
                            description: `description ${Math.random()}`,
                            amount: parseFloat((Math.random() * 100).toFixed(2)),
                            balance: parseFloat((Math.random() * 1000).toFixed(2)),
                            type: ((Math.floor(Math.random() * 2) + 1) === 1) ? 'DEBIT' : 'CREDIT',
                        }
                    })
                }
            })
        }

        res.status(200).json({
            status: "OK",
            payload: r
        })
    }
]

export const registerGetAccountTransactionsRoute = (router: Router) => {
    router.get('/account/:accountId/transactions', ...httpAction)
    console.log('** register GET get account transactions route')
}
