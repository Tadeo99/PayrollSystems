import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatTabsModule } from '@angular/material/tabs';
import { BsHighlightModule } from '@bs/components/highlight';
import { SharedModule } from '@ng-mf/shared/components/core';
import { AnimationsComponent } from 'app/modules/admin/ui/animations/animations.component';

export const routes: Route[] = [
    {
        path     : '',
        component: AnimationsComponent
    }
];

@NgModule({
    declarations: [
        AnimationsComponent
    ],
    imports     : [
        RouterModule.forChild(routes),
        MatButtonModule,
        MatFormFieldModule,
        MatSelectModule,
        MatTabsModule,
        BsHighlightModule,
        SharedModule
    ]
})
export class AnimationsModule
{
}
