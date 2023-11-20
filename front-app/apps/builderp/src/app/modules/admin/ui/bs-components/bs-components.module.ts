import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatSliderModule } from '@angular/material/slider';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatTabsModule } from '@angular/material/tabs';
import { MatTreeModule } from '@angular/material/tree';
import { BsAlertModule } from '../../../../../@bs/components/alert';
import { BsCardModule } from '../../../../../@bs/components/card';
import { BsDrawerModule } from '../../../../../@bs/components/drawer';
import { BsHighlightModule } from '../../../../../@bs/components/highlight';
import { BsLoadingBarModule } from '../../../../../@bs/components/loading-bar';
import { BsMasonryModule } from '../../../../../@bs/components/masonry/masonry.module';
import { BsNavigationModule } from '../../../../../@bs/components/navigation';
import { BsScrollResetModule } from '../../../../../@bs/directives/scroll-reset';
import { SharedModule } from '@ng-mf/shared/components/core'
import { BsComponentsComponent } from '../bs-components/bs-components.component';
import { MockApiComponent } from '../bs-components/libraries/mock-api/mock-api.component';
import { AlertComponent } from '../bs-components/components/alert/alert.component';
import { CardComponent } from '../bs-components/components/card/card.component';
import { DrawerComponent } from '../bs-components/components/drawer/drawer.component';
import { FullscreenComponent } from '../bs-components/components/fullscreen/fullscreen.component';
import { HighlightComponent } from '../bs-components/components/highlight/highlight.component';
import { NavigationComponent } from '../bs-components/components/navigation/navigation.component';
import { MasonryComponent } from '../bs-components/components/masonry/masonry.component';
import { ScrollbarComponent } from '../bs-components/directives/scrollbar/scrollbar.component';
import { ScrollResetComponent } from '../bs-components/directives/scroll-reset/scroll-reset.component';
import { ConfigComponent } from '../bs-components/services/config/config.component';
import { ConfirmationComponent } from '../bs-components/services/confirmation/confirmation.component';
import { LoadingBarComponent } from '../bs-components/components/loading-bar/loading-bar.component';
import { MediaWatcherComponent } from '../bs-components/services/media-watcher/media-watcher.component';
import { SplashScreenComponent } from '../bs-components/services/splash-screen/splash-screen.component';
import { FindByKeyComponent } from '../bs-components/pipes/find-by-key/find-by-key.component';
import { MustMatchComponent } from '../bs-components/validators/must-match/must-match.component';
import { bsComponentsRoutes } from '../bs-components/bs-components.routing';

@NgModule({
    declarations: [
        BsComponentsComponent,
        MockApiComponent,
        AlertComponent,
        CardComponent,
        DrawerComponent,
        FullscreenComponent,
        HighlightComponent,
        LoadingBarComponent,
        MasonryComponent,
        NavigationComponent,
        ScrollbarComponent,
        ScrollResetComponent,
        ConfigComponent,
        ConfirmationComponent,
        SplashScreenComponent,
        MediaWatcherComponent,
        FindByKeyComponent,
        MustMatchComponent
    ],
    imports     : [
        RouterModule.forChild(bsComponentsRoutes),
        MatButtonModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
        MatSelectModule,
        MatSlideToggleModule,
        MatSliderModule,
        MatSidenavModule,
        MatTabsModule,
        MatTreeModule,
        BsAlertModule,
        BsCardModule,
        BsDrawerModule,
        BsHighlightModule,
        BsLoadingBarModule,
        BsMasonryModule,
        BsNavigationModule,
        BsScrollResetModule,
        SharedModule
    ]
})
export class BsComponentsModule
{
}
