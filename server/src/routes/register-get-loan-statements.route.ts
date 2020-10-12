import {Router, Request, Response, NextFunction} from "express";
import {param} from 'express-validator'
import moment from 'moment'

const total = [1,2,3,4,5]

const httpAction: any[] = [
    [
        param("loanId")
    ],
    (req: Request, res: Response, next: NextFunction) => {

        const loanId = req.params.loanId;
        console.log(`GET getLoanStatements loanId = ${loanId}`)

        const m = moment()

        const payload = {
            messages: [
                'Your loan statements are issued every 6 months. Your loan statement will be available for download on 8 Apr 2021. Until then you can download a copy of your recent transactions from the transactions page',
            ],
            statements: total.map((i: number) => {
                return {
                    statementId: i,
                    periodFrom: m.format('DD-MM-YYYY'),
                    periodTo: m.subtract(1, 'year').format('DD-MM-YYYY'),
                    type: 'LOAN_STATEMENT',
                    downloadLink: ''
                }
            })
        }

        res.status(200).json({
            status: "OK",
            payload
        })
    }
]

export const registerGetLoanStatements = (router: Router) => {
    router.get('/loan/:loanId/statements', ...httpAction)
    console.log('** register GET loan statements route')

}