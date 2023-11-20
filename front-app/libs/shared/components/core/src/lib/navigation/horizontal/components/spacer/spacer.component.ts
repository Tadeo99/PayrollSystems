import { ChangeDetectionStrategy, ChangeDetectorRef, Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';
import { BsHorizontalNavigationComponent } from '../../horizontal.component';
import { BsNavigationService } from '../../../navigation.service';
import { BsNavigationItem } from '@ng-mf/shared/service/core';

@Component({
    selector: 'ng-mf-bs-horizontal-navigation-spacer-item',
    templateUrl: './spacer.component.html',
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class BsHorizontalNavigationSpacerItemComponent implements OnInit, OnDestroy {
    @Input() item!: BsNavigationItem;
    @Input() name!: string;

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
}
