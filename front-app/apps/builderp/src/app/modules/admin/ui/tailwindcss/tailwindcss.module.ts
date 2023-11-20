import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { SharedModule } from '@ng-mf/shared/components/core'
import { TailwindCSSComponent } from './tailwindcss.component';

export const routes: Route[] = [
    {
        path     : '',
        component: TailwindCSSComponent
    }
];

@NgModule({
    declarations: [
        TailwindCSSComponent
    ],
    imports     : [
        RouterModule.forChild(routes),
        SharedModule
    ]
})
export class TailwindCSSModule
{
}
