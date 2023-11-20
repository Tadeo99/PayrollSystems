import { Component, OnDestroy, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { BsNavigationItem } from '@bs/components/navigation';
import { MatDrawer } from '@angular/material/sidenav';
import { Subject, takeUntil } from 'rxjs';
import { BsMediaWatcherService } from '../../../../../@bs/services/media-watcher';

@Component({
    selector     : 'ng-mf-bs-components',
    templateUrl  : './bs-components.component.html',
    styleUrls    : ['./bs-components.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class BsComponentsComponent implements OnInit, OnDestroy
{
    @ViewChild('matDrawer', {static: true}) matDrawer: MatDrawer;
    drawerMode: 'side' | 'over';
    drawerOpened: boolean;
    menuData: BsNavigationItem[];
    private _unsubscribeAll: Subject<any> = new Subject<any>();

    /**
     * Constructor
     */
    constructor(
        private _bsMediaWatcherService: BsMediaWatcherService
    )
    {
        this.menuData = [
            {
                id      : 'bs-components.libraries',
                title   : 'Libraries',
                type    : 'group',
                children: [
                    {
                        id   : 'bs-components.libraries.mock-api',
                        title: 'MockAPI',
                        type : 'basic',
                        link : '/ui/bs-components/libraries/mock-api'
                    }
                ]
            },
            {
                id      : 'bs-components.components',
                title   : 'Components',
                type    : 'group',
                children: [
                    {
                        id   : 'bs-components.components.alert',
                        title: 'Alert',
                        type : 'basic',
                        link : '/ui/bs-components/components/alert'
                    },
                    {
                        id   : 'bs-components.components.card',
                        title: 'Card',
                        type : 'basic',
                        link : '/ui/bs-components/components/card'
                    },
                    {
                        id   : 'bs-components.components.drawer',
                        title: 'Drawer',
                        type : 'basic',
                        link : '/ui/bs-components/components/drawer'
                    },
                    {
                        id   : 'bs-components.components.fullscreen',
                        title: 'Fullscreen',
                        type : 'basic',
                        link : '/ui/bs-components/components/fullscreen'
                    },
                    {
                        id   : 'bs-components.components.highlight',
                        title: 'Highlight',
                        type : 'basic',
                        link : '/ui/bs-components/components/highlight'
                    },
                    {
                        id   : 'bs-components.components.loading-bar',
                        title: 'Loading Bar',
                        type : 'basic',
                        link : '/ui/bs-components/components/loading-bar'
                    },
                    {
                        id   : 'bs-components.components.masonry',
                        title: 'Masonry',
                        type : 'basic',
                        link : '/ui/bs-components/components/masonry'
                    },
                    {
                        id   : 'bs-components.components.navigation',
                        title: 'Navigation',
                        type : 'basic',
                        link : '/ui/bs-components/components/navigation'
                    }
                ]
            },
            {
                id      : 'bs-components.directives',
                title   : 'Directives',
                type    : 'group',
                children: [
                    {
                        id   : 'bs-components.directives.scrollbar',
                        title: 'Scrollbar',
                        type : 'basic',
                        link : '/ui/bs-components/directives/scrollbar'
                    },
                    {
                        id   : 'bs-components.directives.scroll-reset',
                        title: 'ScrollReset',
                        type : 'basic',
                        link : '/ui/bs-components/directives/scroll-reset'
                    }
                ]
            },
            {
                id      : 'bs-components.services',
                title   : 'Services',
                type    : 'group',
                children: [
                    {
                        id   : 'bs-components.services.config',
                        title: 'Config',
                        type : 'basic',
                        link : '/ui/bs-components/services/config'
                    },
                    {
                        id   : 'bs-components.services.confirmation',
                        title: 'Confirmation',
                        type : 'basic',
                        link : '/ui/bs-components/services/confirmation'
                    },
                    {
                        id   : 'bs-components.services.splash-screen',
                        title: 'SplashScreen',
                        type : 'basic',
                        link : '/ui/bs-components/services/splash-screen'
                    },
                    {
                        id   : 'bs-components.services.media-watcher',
                        title: 'MediaWatcher',
                        type : 'basic',
                        link : '/ui/bs-components/services/media-watcher'
                    }
                ]
            },
            {
                id      : 'bs-components.pipes',
                title   : 'Pipes',
                type    : 'group',
                children: [
                    {
                        id   : 'bs-components.pipes.find-by-key',
                        title: 'FindByKey',
                        type : 'basic',
                        link : '/ui/bs-components/pipes/find-by-key'
                    }
                ]
            },
            {
                id      : 'bs-components.validators',
                title   : 'Validators',
                type    : 'group',
                children: [
                    {
                        id   : 'bs-components.validators.must-match',
                        title: 'MustMatch',
                        type : 'basic',
                        link : '/ui/bs-components/validators/must-match'
                    }
                ]
            }
        ];
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Lifecycle hooks
    // -----------------------------------------------------------------------------------------------------

    /**
     * On init
     */
    ngOnInit(): void
    {
        // Subscribe to media query change
        this._bsMediaWatcherService.onMediaChange$
            .pipe(takeUntil(this._unsubscribeAll))
            .subscribe(({matchingAliases}) => {

                // Set the drawerMode and drawerOpened
                if ( matchingAliases.includes('md') )
                {
                    this.drawerMode = 'side';
                    this.drawerOpened = true;
                }
                else
                {
                    this.drawerMode = 'over';
                    this.drawerOpened = false;
                }
            });
    }

    /**
     * On destroy
     */
    ngOnDestroy(): void
    {
        // Unsubscribe from all subscriptions
        this._unsubscribeAll.next(null);
        this._unsubscribeAll.complete();
    }
}
