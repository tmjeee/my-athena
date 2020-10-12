import {NextFunction, Router, Request, Response} from "express";
import { param } from "express-validator";
import path from 'path'
import stream from "stream"
import fs from "fs"

const httpAction: any[] = [
    [
        param("id").exists(),
    ],
    (req: Request, res: Response, next: NextFunction) => {
        const id = req.params['id']

        const f = path.resolve(path.join(__dirname, '..'));

        res.sendFile(`${f}/assets/${id}`, (err) => {
            if (err) {
                console.log(err)
                res.sendStatus(400)
            }
        })
    }
]

export const registerGetPhotoById = (router: Router) => {
    router.get('/photos/:id', ...httpAction)
    console.log('** register GET photo by id route')
}
