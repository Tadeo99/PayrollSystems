import { Injectable } from '@angular/core';
import { cloneDeep } from 'lodash-es';
import { BsMockApiService } from '../../../../@bs/lib/mock-api';
import { analytics as analyticsData } from './data';

@Injectable({
    providedIn: 'root'
})
export class AnalyticsMockApi {
    private _analytics: any = analyticsData;

    /**
     * Constructor
     */
    constructor(private _bsMockApiService: BsMockApiService) {
        // Register Mock API handlers
        this.registerHandlers();
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Register Mock API handlers
     */
    registerHandlers(): void {
        // -----------------------------------------------------------------------------------------------------
        // @ Sales - GET
        // -----------------------------------------------------------------------------------------------------
        this._bsMockApiService
            .onGet('api/dashboards/analytics')
            .reply(() => [200, cloneDeep(this._analytics)]);
    }
}
