import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BsLoadingBarModule } from '@ng-mf/shared/components/core';
import { SharedModule } from '@ng-mf/shared/components/core'
import { EmptyLayoutComponent } from './empty.component';

@NgModule({
    declarations: [
        EmptyLayoutComponent
    ],
    imports: [
        RouterModule,
        BsLoadingBarModule,
        SharedModule
    ],
    exports: [
        EmptyLayoutComponent
    ]
})
export class EmptyLayoutModule {
}
