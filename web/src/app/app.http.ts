import {HttpEvent, HttpHandlerFn, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";

/**
 * Intercepts all requests to the API and rewrites the URL to the local API.
 *
 * It is for only debugging purposes.
 * @param req request
 * @param next chain request
 */
export function apiIntercept(req: HttpRequest<any>, next: HttpHandlerFn): Observable<HttpEvent<any>> {
  // uncomment below to use local API
  // req = req.clone({
  //   url: `http://localhost:8080/${req.url}`
  // })
  return next(req);
}
