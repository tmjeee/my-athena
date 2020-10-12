import {Router, Request, Response, NextFunction} from "express";
import {param} from 'express-validator'
import moment from 'moment'

const httpAction: any[] = [
   [
       param("accountId"),
   ],
   (req: Request, res: Response, next: NextFunction) => {
       const accountId = req.params.accountId;
       console.log(`GET getLoanDetails accountId = ${accountId}`)
       const payload = {
            messages: [
                'If you need to make changes to your loan call us on 133535 or email hello@myathena.com'
            ],
            loanId: 1,
            extraPayment: parseFloat((Math.random() * 100).toFixed(2)),
            totalMonthlyPayment: 2010,
            minMonthlyRepayment: 2000,
            nextInterestPosting: moment().add(1, 'month').format('DD-MM-YYYY'),
            typeOfRepayment: 'PRINCIPAL_AND_INTEREST',
            interestRate: 2.35,
            loanType: 'OWNER_OCCUPIED',
            loanTerm: '30 Years',
            termRemaining: '25 Years',
            loanStartDate: moment().subtract(5, 'years').subtract(2, 'months').format('DD-MM-YYYY'),
            originalLoanAmount: 500000,
            amountLeftToPayOff: 450000,
            accountHolders: [
                'James Citizen'
            ],
            interestsChargedByFinancialYear: {
                "2001-2002": 10,
                "2002-2004": 20,
                "2004-2006": 30,
            },
            features: [
                'Unlimited additional repayments',
                'Fee-free redraw facility',
                'No Athena fees'
            ]
        }

        res.status(200).json({
            status: "OK",
            payload
        })
   }
]

export const registerGetLoanDetails = (router: Router) => {
    router.get('/account/:accountId/loan-details', ...httpAction)
    console.log('** register GET loan details route')
}