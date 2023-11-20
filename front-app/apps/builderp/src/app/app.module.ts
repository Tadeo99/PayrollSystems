import { NgModule } from '@angular/core';
//import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ExtraOptions, PreloadAllModules, RouterModule } from '@angular/router';
import { BsModule } from '../@bs';
import { BsConfigModule } from '@ng-mf/shared/service/core';
import { BsMockApiModule } from '../@bs/lib/mock-api';
import { CoreModule } from './core/core.module';
import { appConfig } from './core/config/app.config';
import { mockApiServices } from './mock-api';
import { LayoutModule } from './layout/layout.module';
import { AppComponent } from './app.component';
import { appRoutes } from './app.builderp.routing';

const routerConfig: ExtraOptions = {
    preloadingStrategy: PreloadAllModules,
    scrollPositionRestoration: 'enabled'
};

@NgModule({
    declarations: [
        AppComponent
    ],
    imports: [
        //BrowserModule,
        BrowserAnimationsModule,
        RouterModule.forRoot(appRoutes, routerConfig),

        // Bs, BsConfig & BsMockAPI
        BsModule,
        BsConfigModule.forRoot(appConfig),
        BsMockApiModule.forRoot(mockApiServices),

        // Core module of your application
        CoreModule,

        // Layout module of your application
        LayoutModule
    ],
    bootstrap: [
        AppComponent
    ]
})
export class AppModule {
}
