import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservationTimelineComponent } from './reservation-timeline.component';

describe('ReservationTimelineComponent', () => {
  let component: ReservationTimelineComponent;
  let fixture: ComponentFixture<ReservationTimelineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReservationTimelineComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReservationTimelineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
