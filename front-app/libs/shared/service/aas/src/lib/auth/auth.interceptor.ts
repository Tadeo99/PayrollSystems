import { Injectable } from '@angular/core';
import { HttpErrorResponse, HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { AuthService } from './auth.service';
import { AuthUtils } from './auth.utils';
import { environment } from '../../../../../environments/environment';

export const TOKEN_NAME = 'jwt_token';
export const TOKEN_NAME_KEY_WEBSOKECT = 'jwtkeyWebSockect';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    public puerto: string = environment.apiPort;//"4000";//8084
    private urlBase: string = "" + environment.apiHttp + "" + environment.apiIp + ":" + this.puerto + "/" + environment.apiUrlConexto + "/rest/";
    public apiProxyUrl = this.urlBase;
    private authorization = '';

    /**
     * Constructor
     */
    constructor(private _authService: AuthService) {
        //
    }

    /**
     * Intercept
     *
     * @param req
     * @param next
     */
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // Clone the request object
        const headersParam = this.setHeaders(false);
        let newReq = req.clone();

        console.log("this.verificarUrl(req)");
        if (this.verificarUrl(req)) {
            console.log("this.verificarUrl(req)..req.clone");
            newReq = req.clone({ headers: headersParam, reportProgress: true, url: this.getProxyUrl(req) });
        }
        // Request
        //
        // If the access token didn't expire, add the Authorization header.
        // We won't add the Authorization header if the access token expired.
        // This will force the server to return a "401 Unauthorized" response
        // for the protected API routes which our response interceptor will
        // catch and delete the access token from the local storage while logging
        // the user out from the app.
        if (this._authService.accessToken && !AuthUtils.isTokenExpired(this._authService.accessToken)) {
            if (!this.verificarUrl(req)) {
                newReq = req.clone({
                    headers: req.headers.set('Authorization', 'Bearer ' + this._authService.accessToken)
                });
            }
        }

        // Response
        return next.handle(newReq).pipe(
            catchError((error) => {

                // Catch "401 Unauthorized" responses
                if (error instanceof HttpErrorResponse && error.status === 401) {
                    // Sign out
                    this._authService.signOut();

                    // Reload the app
                    location.reload();
                }

                return throwError(error);
            })
        );
    }
    private verificarUrl(request: HttpRequest<any>): boolean {
        const currentUrl = request.url;
        console.log(" currentUrl " + currentUrl);
        const dataTmp: any = environment.apiServicio;
        const serviceName = currentUrl.substring(0, currentUrl.indexOf("/"))
        return (dataTmp["API_URL_" + serviceName.toUpperCase()])?.length> 0 ;
    }

    private getBase(request: HttpRequest<any>): string {
        const currentUrl = request.url;
        let urlBase: string = "" + environment.apiHttp + "" + environment.apiIp + ":" + this.puerto + "/" + environment.apiUrlConexto + "/rest/";
        if (environment.isMicroServicio == false) {
            const dataTmp: any = environment.apiServicio;
            if (this.verificarUrl(request)) {
                const serviceName = currentUrl.substring(0, currentUrl.indexOf("/"))
                urlBase = dataTmp["API_URL_" + serviceName.toUpperCase()];
            }
        }
        this.apiProxyUrl = urlBase;
        return urlBase;
    }

    private getProxyUrl(request: HttpRequest<any>): string {
        this.getBase(request);
        let currentUrl: string = request.url;
        if (!currentUrl.includes('/' + environment.apiUrlConexto + '/')) {
            if (environment.isMicroServicio == false && this.apiProxyUrl.length > 0) {
                currentUrl = this.apiProxyUrl + currentUrl.substring(currentUrl.indexOf("/") + 1);
            } else {
                currentUrl = this.apiProxyUrl + currentUrl;
            }
        }
        return currentUrl;
    }

    private setHeaders(esMultiPart: boolean): HttpHeaders {
        const contentType = esMultiPart == true ? 'multipart/form-data;boundary=----WebKitFormBoundaryl4oxlVSt9yblG8VC' : 'application/json'
        const contentAcept = esMultiPart == true ? 'multipart/form-data' : 'application/json'
        const headers = new HttpHeaders({
            'Content-Type': '' + contentType + '',
            'Accept': '' + contentAcept + '',
            'service-key': '' + environment.service_key + '',
            'auth-token': '' + this._authService.accessToken + '',
            'Authorization': '' + this.authorization + '',
            'version': '' + environment.version + '',
            'websockect-key': '' + this.getToken(TOKEN_NAME_KEY_WEBSOKECT) + '',
        });
        return headers;
    }

    private getToken(key: string): string {
        return window.localStorage[key];
    }
}
