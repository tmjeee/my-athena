import {NextFunction, Router, Request, Response} from "express";
import { param } from "express-validator";

const r: number[] = [...Array(100).keys()].map((i) => i)
const p: string[] = [
    'image-01.jpg',
    'image-02.jpg',
    'image-03.jpg',
    'image-04.jpg',
    'image-05.jpg',
    'image-06.jpg',
    'image-07.jpg',
];

const httpAction: any[] = [
    [
        param('accountId').exists().isNumeric()
    ],
    (req: Request, res: Response, next: NextFunction) => {
        const accountId = req.params.accountId
        const limit = Number(req.query.limit ? req.query.limit : 10);
        const offset = Number(req.query.offset ? req.query.offset : 0);

        setTimeout(() => {
            res.status(200).json(
                {
                    accountId,
                    total: r.length,
                    limit,
                    offset,
                    photos: r.slice(offset, (offset + limit)).map(i => ({
                        photo: {
                            id: i,
                            imageUrls: p.map((a) => `http://192.168.122.1:9999/api/photos/${a}`),
                            description: `individual photo description ${Math.random()}`
                        },
                        description: `A photo set ${i} description ${Math.random()}`
                    }))
                }
            )
        }, 2000)
    }
]

export const registerGetPhoto = (router: Router) => {
    router.get('/account/:accountId/photos', ...httpAction)
    console.log('** register GET photos route')
}