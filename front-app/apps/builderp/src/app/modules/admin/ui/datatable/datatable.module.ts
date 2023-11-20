import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { SharedModule } from '@ng-mf/shared/components/core'
import { DatatableComponent } from './datatable.component';

export const routes: Route[] = [
    {
        path     : '',
        component: DatatableComponent
    }
];

@NgModule({
    declarations: [
        DatatableComponent
    ],
    imports     : [
        RouterModule.forChild(routes),
        SharedModule
    ]
})
export class DatatableModule
{
}
