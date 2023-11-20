import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { BsHighlightModule } from '@bs/components/highlight';
import { SharedModule } from '@ng-mf/shared/components/core';
import { AdvancedSearchComponent } from 'app/modules/admin/ui/advanced-search/advanced-search.component';

export const routes: Route[] = [
    {
        path     : '',
        component: AdvancedSearchComponent
    }
];

@NgModule({
    declarations: [
        AdvancedSearchComponent
    ],
    imports     : [
        RouterModule.forChild(routes),
        MatButtonModule,
        MatCheckboxModule,
        MatFormFieldModule,
        MatInputModule,
        MatSelectModule,
        BsHighlightModule,
        SharedModule
    ]
})
export class AdvancedSearchModule
{
}
