import {NextFunction, Router, Request, Response} from "express";
import { body } from "express-validator";

const httpActions: any[] = [
    [
        body("username").exists(),
        body('password').exists()
    ],
    (req: Request, res: Response, next: NextFunction) => {
        const username = req.body.username;
        const password = req.body.password;
        console.log(`********** LOGIN ${username} ${password}`)
        if (username === "unknown")
            res.status(200).json({
                status: "Not Ok"
            })
        else {
            res.status(200).json({
                status: "OK",
                payload: {
                    userId: 1
                }
            });
        }
    }
];

export const registerLoginRoute = (router: Router) => {
    router.post('/login', ...httpActions)
    console.log('** register POST login route')
};