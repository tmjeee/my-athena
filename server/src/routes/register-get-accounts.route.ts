import {NextFunction, Router, Request, Response} from "express";
import { param } from "express-validator";
import  moment from 'moment'


const httpAction: any[] = [
    [
        param("userId").exists()
    ],
    (req: Request, res: Response, next: NextFunction) => {
        const userId: string = req.params.userId;
        console.log(`GET getAccounts userId = ${userId}`)
        res.status(200).json({
                status: "OK",
                payload:
                    [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26].map((i) => {
                        return {
                            userId: i,
                            accountId: i,
                            accountName: `Account #${i}`,
                            amountOutstanding: (i* 1000),
                            amountAvailable: (i * 2000),
                            monthlyRepayment: (1 * 100),
                            bsb: `1010${i}`,
                            accountNumber: `2020202${i}`,
                            accountAddress: `Unit ${i}, Street ${i}, State ${i}, Country`,
                            nextDueDate: moment().add(i, 'day').format('DD-MM-YYYY')
                        }
                    })
            }
        )
    }
]

export const registerGetAccountsRoute = (router: Router) => {
    router.get('/user/:userId/accounts', ...httpAction)
}