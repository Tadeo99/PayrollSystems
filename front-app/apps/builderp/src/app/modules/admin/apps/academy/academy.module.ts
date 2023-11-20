import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatSelectModule } from '@angular/material/select';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatTooltipModule } from '@angular/material/tooltip';
import { BsFindByKeyPipeModule } from '@ng-mf/shared/bs';
import { SharedModule } from '@ng-mf/shared/components/core';
import { academyRoutes } from './academy.routing';
import { AcademyComponent } from './academy.component';
import { AcademyDetailsComponent } from './details/details.component';
import { AcademyListComponent } from './list/list.component';
import { MatTabsModule } from '@angular/material/tabs';

@NgModule({
    declarations: [
        AcademyComponent,
        AcademyDetailsComponent,
        AcademyListComponent
    ],
    imports: [
        RouterModule.forChild(academyRoutes),
        MatButtonModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
        MatProgressBarModule,
        MatSelectModule,
        MatSidenavModule,
        MatSlideToggleModule,
        MatTooltipModule,
        BsFindByKeyPipeModule,
        SharedModule,
        MatTabsModule
    ]
})
export class AcademyModule {
}
