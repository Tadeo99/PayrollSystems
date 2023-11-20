import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { BsCardModule, SharedModule } from '@ng-mf/shared/components/core';
import { AuthSignOutComponent } from './sign-out.component';
import { authSignOutRoutes } from './sign-out.routing';

@NgModule({
    declarations: [
        AuthSignOutComponent
    ],
    imports: [
        RouterModule.forChild(authSignOutRoutes),
        MatButtonModule,
        BsCardModule,
        SharedModule
    ]
})
export class AuthSignOutModule {
}
