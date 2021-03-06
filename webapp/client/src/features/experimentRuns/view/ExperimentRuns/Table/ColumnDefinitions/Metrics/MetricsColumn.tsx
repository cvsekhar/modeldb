import withProps from 'core/shared/utils/react/withProps';

import SimpleKeyValuesColumn from '../shared/SimpleKeyValuesColumn/SimpleKeyValuesColumn';

const MetricsColumn = withProps(SimpleKeyValuesColumn)({ type: 'metrics' });

export default MetricsColumn;
