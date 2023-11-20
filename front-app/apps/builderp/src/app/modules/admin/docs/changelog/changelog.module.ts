import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SharedModule } from '@ng-mf/shared/components/core';
import { ChangelogComponent } from 'app/modules/admin/docs/changelog/changelog';
import { changelogRoutes } from 'app/modules/admin/docs/changelog/changelog.routing';

@NgModule({
    declarations: [
        ChangelogComponent
    ],
    imports     : [
        RouterModule.forChild(changelogRoutes),
        SharedModule
    ]
})
export class ChangelogModule
{
}
