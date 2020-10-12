import {NextFunction, Router, Request, Response} from "express";
import { param } from "express-validator";

const httpActions: any[] = [
   [
      param("userId").exists()
   ],
   (req: Request, res: Response, next: NextFunction) => {
      const userId = req.params.userId;
      console.log(`GET requestPin ${userId}`)
      res.status(200).json({
         status: "OK"
      });
   }
]

export const registerRequestPinRoute = (router: Router) => {
   router.get('/requestPin/:userId', ...httpActions)
   console.log('** register GET requestPin route')
}