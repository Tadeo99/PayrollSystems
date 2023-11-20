import { Injectable } from '@angular/core';
import { CanMatch, Route, Router, UrlSegment, UrlTree } from '@angular/router';
import { Observable, of, switchMap } from 'rxjs';
import { AuthService } from '../auth.service';
import { TitleService } from '@ng-mf/shared/service/core';

@Injectable({
    providedIn: 'root'
})
export class AuthGuard implements CanMatch {
    /**
     * Constructor
     */
    constructor(
        private _authService: AuthService,
        private _router: Router,
        private _titleService: TitleService
    ) {
        //
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Can match
     *
     * @param route
     * @param segments
     */
    canMatch(route: Route, segments: UrlSegment[]): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
        return this._check(segments);
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Private methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Check the authenticated status
     *
     * @param segments
     * @private
     */
    private _check(segments: UrlSegment[]): Observable<boolean | UrlTree> {
        // Check the authentication status
        return this._authService.check().pipe(
            switchMap((authenticated) => {

                // If the user is not authenticated...
                if (!authenticated) {
                    // Redirect to the sign-in page with a redirectUrl param
                    const redirectURL = `/${segments.join('/')}`;
                    const urlTree = this._router.parseUrl(`sign-in?redirectURL=${redirectURL}`);

                    return of(urlTree);
                }
               // this._titleService.setTitleActiveMenuRouter();
                // Allow the access
                return of(true);
            })
        );
    }
}
