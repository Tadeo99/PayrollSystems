import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { MatTabsModule } from '@angular/material/tabs';
import { BsHighlightModule } from '../../../../../@bs/components/highlight';
import { SharedModule } from '@ng-mf/shared/components/core'
import { TypographyComponent } from '../typography/typography.component';

export const routes: Route[] = [
    {
        path     : '',
        component: TypographyComponent
    }
];

@NgModule({
    declarations: [
        TypographyComponent
    ],
    imports     : [
        RouterModule.forChild(routes),
        MatTabsModule,
        BsHighlightModule,
        SharedModule
    ]
})
export class TypographyModule
{
}
