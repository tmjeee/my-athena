import {NextFunction, Router, Request, Response} from "express";
import { body } from "express-validator";

const httpAction: any[] = [
    [
       body("pin").exists(),
       body("userId").exists()
    ],
    (req: Request, res: Response, next: NextFunction) => {
        const pin: String = req.body.pin;
        const username: String = req.body.userId;

        console.log(`POST submitPin ${pin} ${username}`)

        res.status(200).json({
            status: "OK",
            payload: {   // User domain object
                userId: 1,
                username: "tmjeee"
            }
        });
    }
];

export const registerSubmitPinRoute = (router: Router) => {
    router.post("/submitPin", ...httpAction);
    console.log('** register POST submitPin route')
}
