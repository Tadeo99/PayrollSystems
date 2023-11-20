import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatTooltipModule } from '@angular/material/tooltip';
import { BsScrollbarModule } from '@ng-mf/shared/bs';
import { BsHorizontalNavigationBasicItemComponent } from './horizontal/components/basic/basic.component';
import { BsHorizontalNavigationBranchItemComponent } from './horizontal/components/branch/branch.component';
import { BsHorizontalNavigationDividerItemComponent } from './horizontal/components/divider/divider.component';
import { BsHorizontalNavigationSpacerItemComponent } from './horizontal/components/spacer/spacer.component';
import { BsHorizontalNavigationComponent } from './horizontal/horizontal.component';
import { BsVerticalNavigationAsideItemComponent } from './vertical/components/aside/aside.component';
import { BsVerticalNavigationBasicItemComponent } from './vertical/components/basic/basic.component';
import { BsVerticalNavigationCollapsableItemComponent } from './vertical/components/collapsable/collapsable.component';
import { BsVerticalNavigationDividerItemComponent } from './vertical/components/divider/divider.component';
import { BsVerticalNavigationGroupItemComponent } from './vertical/components/group/group.component';
import { BsVerticalNavigationSpacerItemComponent } from './vertical/components/spacer/spacer.component';
import { BsVerticalNavigationComponent } from './vertical/vertical.component';

@NgModule({
    declarations: [
        BsHorizontalNavigationBasicItemComponent,
        BsHorizontalNavigationBranchItemComponent,
        BsHorizontalNavigationDividerItemComponent,
        BsHorizontalNavigationSpacerItemComponent,
        BsHorizontalNavigationComponent,
        BsVerticalNavigationAsideItemComponent,
        BsVerticalNavigationBasicItemComponent,
        BsVerticalNavigationCollapsableItemComponent,
        BsVerticalNavigationDividerItemComponent,
        BsVerticalNavigationGroupItemComponent,
        BsVerticalNavigationSpacerItemComponent,
        BsVerticalNavigationComponent
    ],
    imports: [
        CommonModule,
        RouterModule,
        MatButtonModule,
        MatDividerModule,
        MatIconModule,
        MatMenuModule,
        MatTooltipModule,
        BsScrollbarModule
    ],
    exports: [
        BsHorizontalNavigationComponent,
        BsVerticalNavigationComponent
    ]
})
export class BsNavigationModule {
}
