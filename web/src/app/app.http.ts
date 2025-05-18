import {HttpEvent, HttpHandlerFn, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {isDevMode} from "@angular/core";

/**
 * Intercepts all requests to the API and rewrites the URL to the local API.
 *
 * It is for only debugging purposes.
 * @param req request
 * @param next chain request
 */
export function apiIntercept(req: HttpRequest<any>, next: HttpHandlerFn): Observable<HttpEvent<any>> {
  // uncomment below to use local API
  if (isDevMode() && req.url.startsWith("/")) {
    let port = 8080
    req = req.clone({
      url: `http://localhost:${port}${req.url}`
    });
  }
  return next(req);
}
