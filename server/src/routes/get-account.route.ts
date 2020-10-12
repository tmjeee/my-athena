import {NextFunction, Router, Request, Response} from "express";
import { param } from "express-validator";
import moment from 'moment'


const httpAction: any[] = [
    [
        param("userId").exists(),
        param("acountId").exists()
    ],
    (req: Request, res: Response, next: NextFunction) => {
        const userId = parseInt(req.params.userId);
        const accountId = parseInt(req.params.accountId);

        console.log(`GET getAccount userId = ${userId} accountId=${accountId}`)

        res.status(200).json({
            userId,
            accountId,
            accountName: `Account for id ${accountId}`,
            accountOutstanding: parseFloat((Math.random() * 10000).toFixed(2)),
            amountAvailable: parseFloat((Math.random() * 1000).toFixed(2)),
            bsb: `001-00-${accountId}`,
            accountNumber: `1010101010-${accountId}`,
            accountAddress: `unit-${accountId}, Some Street, Some state`,
            monthlyRepayment: parseFloat((Math.random() * 100).toFixed(2)),
            nextDueDate: moment().add(2, 'days').format('DD-MM-YYYY'),
        })
    }
]

export const registerGetAccountRoute = (router: Router) => {
    router.get(`/user/:userId/account/:accountId`, ...httpAction)
    console.log('** register GET get account route')
}