import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { MatRippleModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';
import { MatTabsModule } from '@angular/material/tabs';
import { BsHighlightModule } from '../../../../../@bs/components/highlight';
import { SharedModule } from '@ng-mf/shared/components/core'
import { ColorsComponent } from './colors.component';

export const routes: Route[] = [
    {
        path     : '',
        component: ColorsComponent
    }
];

@NgModule({
    declarations: [
        ColorsComponent
    ],
    imports     : [
        RouterModule.forChild(routes),
        MatIconModule,
        MatRippleModule,
        MatTabsModule,
        BsHighlightModule,
        SharedModule
    ]
})
export class ColorsModule
{
}
