import { ChangeDetectionStrategy, ChangeDetectorRef, Component, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { BooleanInput } from '@angular/cdk/coercion';
import { MatMenu } from '@angular/material/menu';
import { Subject, takeUntil } from 'rxjs';
import { BsHorizontalNavigationComponent } from '../../horizontal.component';
import { BsNavigationService } from '../../../navigation.service';
import { BsNavigationItem } from '@ng-mf/shared/service/core';

@Component({
    selector: 'ng-mf-bs-horizontal-navigation-branch-item',
    templateUrl: './branch.component.html',
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class BsHorizontalNavigationBranchItemComponent implements OnInit, OnDestroy {
    /* eslint-disable @typescript-eslint/naming-convention */
    static ngAcceptInputType_child: BooleanInput;
    /* eslint-enable @typescript-eslint/naming-convention */

    @Input() child = false;
    @Input() item!: BsNavigationItem;
    @Input() name!: string;
    @ViewChild('matMenu', { static: true }) matMenu!: MatMenu;

    private _bsHorizontalNavigationComponent!: BsHorizontalNavigationComponent;
    private _unsubscribeAll: Subject<any> = new Subject<any>();

    /**
     * Constructor
     */
    constructor(
        private _changeDetectorRef: ChangeDetectorRef,
        private _bsNavigationService: BsNavigationService
    ) {
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Lifecycle hooks
    // -----------------------------------------------------------------------------------------------------

    /**
     * On init
     */
    ngOnInit(): void {
        // Get the parent navigation component
        this._bsHorizontalNavigationComponent = this._bsNavigationService.getComponent(this.name);

        // Subscribe to onRefreshed on the navigation component
        this._bsHorizontalNavigationComponent.onRefreshed.pipe(
            takeUntil(this._unsubscribeAll)
        ).subscribe(() => {

            // Mark for check
            this._changeDetectorRef.markForCheck();
        });
    }

    /**
     * On destroy
     */
    ngOnDestroy(): void {
        // Unsubscribe from all subscriptions
        this._unsubscribeAll.next(null);
        this._unsubscribeAll.complete();
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Trigger the change detection
     */
    triggerChangeDetection(): void {
        // Mark for check
        this._changeDetectorRef.markForCheck();
    }

    /**
     * Track by function for ngFor loops
     *
     * @param index
     * @param item
     */
    trackByFn(index: number, item: any): any {
        return item.id || index;
    }
}
