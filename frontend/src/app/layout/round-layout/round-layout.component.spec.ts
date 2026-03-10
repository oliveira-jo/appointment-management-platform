import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoundLayoutComponent } from './round-layout.component';

describe('RoundLayoutComponent', () => {
  let component: RoundLayoutComponent;
  let fixture: ComponentFixture<RoundLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RoundLayoutComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RoundLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
