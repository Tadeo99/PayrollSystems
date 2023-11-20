import { Route } from '@angular/router';
import { BsComponentsComponent } from '../bs-components/bs-components.component';
import { MockApiComponent } from '../bs-components/libraries/mock-api/mock-api.component';
import { AlertComponent } from '../bs-components/components/alert/alert.component';
import { CardComponent } from '../bs-components/components/card/card.component';
import { DrawerComponent } from '../bs-components/components/drawer/drawer.component';
import { FullscreenComponent } from '../bs-components/components/fullscreen/fullscreen.component';
import { HighlightComponent } from '../bs-components/components/highlight/highlight.component';
import { LoadingBarComponent } from '../bs-components/components/loading-bar/loading-bar.component';
import { MasonryComponent } from '../bs-components/components/masonry/masonry.component';
import { NavigationComponent } from '../bs-components/components/navigation/navigation.component';
import { ScrollbarComponent } from '../bs-components/directives/scrollbar/scrollbar.component';
import { ScrollResetComponent } from '../bs-components/directives/scroll-reset/scroll-reset.component';
import { ConfigComponent } from '../bs-components/services/config/config.component';
import { ConfirmationComponent } from '../bs-components/services/confirmation/confirmation.component';
import { MediaWatcherComponent } from '../bs-components/services/media-watcher/media-watcher.component';
import { SplashScreenComponent } from '../bs-components/services/splash-screen/splash-screen.component';
import { FindByKeyComponent } from '../bs-components/pipes/find-by-key/find-by-key.component';
import { MustMatchComponent } from '../bs-components/validators/must-match/must-match.component';

export const bsComponentsRoutes: Route[] = [
    {
        path     : '',
        component: BsComponentsComponent,
        children : [
            {
                path      : '',
                pathMatch : 'full',
                redirectTo: 'libraries/mock-api'
            },
            {
                path    : 'libraries',
                children: [
                    {
                        path     : 'mock-api',
                        component: MockApiComponent
                    }
                ]
            },
            {
                path    : 'components',
                children: [
                    {
                        path      : '',
                        pathMatch : 'full',
                        redirectTo: 'alert'
                    },
                    {
                        path     : 'alert',
                        component: AlertComponent
                    },
                    {
                        path     : 'card',
                        component: CardComponent
                    },
                    {
                        path     : 'drawer',
                        component: DrawerComponent
                    },
                    {
                        path     : 'fullscreen',
                        component: FullscreenComponent
                    },
                    {
                        path     : 'highlight',
                        component: HighlightComponent
                    },
                    {
                        path     : 'loading-bar',
                        component: LoadingBarComponent
                    },
                    {
                        path     : 'masonry',
                        component: MasonryComponent
                    },
                    {
                        path     : 'navigation',
                        component: NavigationComponent
                    }
                ]
            },
            {
                path    : 'directives',
                children: [
                    {
                        path      : '',
                        pathMatch : 'full',
                        redirectTo: 'scrollbar'
                    },
                    {
                        path     : 'scrollbar',
                        component: ScrollbarComponent
                    },
                    {
                        path     : 'scroll-reset',
                        component: ScrollResetComponent
                    }
                ]
            },
            {
                path    : 'services',
                children: [
                    {
                        path      : '',
                        pathMatch : 'full',
                        redirectTo: 'config'
                    },
                    {
                        path     : 'config',
                        component: ConfigComponent
                    },
                    {
                        path     : 'confirmation',
                        component: ConfirmationComponent
                    },
                    {
                        path     : 'splash-screen',
                        component: SplashScreenComponent
                    },
                    {
                        path     : 'media-watcher',
                        component: MediaWatcherComponent
                    }
                ]
            },
            {
                path    : 'pipes',
                children: [
                    {
                        path      : '',
                        pathMatch : 'full',
                        redirectTo: 'find-by-key'
                    },
                    {
                        path     : 'find-by-key',
                        component: FindByKeyComponent
                    }
                ]
            },
            {
                path    : 'validators',
                children: [
                    {
                        path      : '',
                        pathMatch : 'full',
                        redirectTo: 'must-match'
                    },
                    {
                        path     : 'must-match',
                        component: MustMatchComponent
                    }
                ]
            }
        ]
    }
];
