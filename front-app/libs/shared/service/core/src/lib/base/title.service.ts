import { Injectable } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router, Scroll } from '@angular/router';
import { Title } from '@angular/platform-browser';
import { TranslocoService } from '@ngneat/transloco';
import { filter, map, Observable, ReplaySubject } from 'rxjs';

/* Data for a menu item */
export interface MenuItem {
  path?: string; /* The URL path to the page */
  title?: string; /* The Title of the page */
  icon?: string; /* An optional icon for the page title */
}
/**
 * La Class LoginService.
 * <ul>
 * <li>Copyright 2016 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Mon Jan 16 21:01:47 COT 2017
 * @since ERP-CRIS 1.0
 */
@Injectable({
  providedIn: 'root',
})
export class TitleService {

  private _activeMenuItem: ReplaySubject<MenuItem | undefined> = new ReplaySubject<MenuItem | undefined>(1);
  constructor(public router: Router, public activatedRoute: ActivatedRoute,
    private titleService: Title, public _translocoService: TranslocoService) {
    //
  }

  public setTitleActiveMenuRouter() {
    try {
      this.router.events.pipe(filter(e => e instanceof Scroll)).subscribe((e: any) => {
        if (e.routerEvent instanceof NavigationEnd) {
          const active = this.lastRouteWithMenuItem(this.router?.routerState?.root);
          if (active != null) {
            const title = active.title ?? '';
            this.setTitle(title);
          }
          this.activeMenuItem = active;
        }

      });

    } catch (error) {
      console.log(error);
    }
  }
  public setTitle(title: string) {
    if (this._translocoService.translate(title) != null) {
      title = this._translocoService.translate(title);
    }
    this.titleService.setTitle("Build ERP :: " + title);
  }
  private lastRouteWithMenuItem(route: ActivatedRoute | null): MenuItem | undefined {
    if (route != null) {
      let lastMenu;
      do {
        lastMenu = this.extractMenu(route) || lastMenu;
      }
      while ((route = route.firstChild));
      return lastMenu;
    }
    return undefined;
  }

  private extractMenu(route: ActivatedRoute | null): MenuItem | undefined {
    const cfg = route?.routeConfig;
    return cfg && cfg.data && cfg?.data?.['title']
      ? { path: cfg?.path, title: cfg.data?.['title'], icon: cfg?.data?.['icon'] }
      : undefined;
  }

  /**
     * Setter & getter for user
     *
     * @param value
     */
  set activeMenuItem(value: MenuItem | undefined) {
    // Store the value
    this._activeMenuItem.next(value);
  }

  get activeMenuItem$(): Observable<MenuItem | undefined> {
    return this._activeMenuItem.asObservable();
  }

}
